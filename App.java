package pierce.sustar;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class App {
    private final static String[] FILTERS = {"demc*", "dapp*", "ap9p"};
    private final static String ERRORNOPATERN = "\\s*Error\\s+#[0-9]+\\s*";
    private final static String FUNCNUM = "funcNum";
    private final static String FUNCNO = "funcNo";
    private final static String ERRORNOTMATCHED = "ErrorsNotMatched";

    public static void main(String[] args) {
        File in = new File("d:\\error1.log");
        File out = new File("d:\\result.log");
        writeErrorResult(in, out, FILTERS);
    }

    private static void writeErrorResult(File in, File out, String[] filters) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(in));
            String errorNo = null;
            String str;
            Map<String,Map<String,String>> errorFunc = new HashMap<String, Map<String, String>>(10);
            while ((str = reader.readLine()) != null)
            {
                int index = 0;
                if(!"".equals(str))
                {
                    //新的错误记录
                    if(str.matches(ERRORNOPATERN))
                    {
                        errorNo = str.replace("\\s","").replace("Error","");
                        continue;
                    }
                    for (String filter : filters) {
                        //函数名
                        if (str.matches("[\\s_0-9a-zA-Z]*" + filter.replace("\\*","") + "[\\s_0-9a-zA-Z]*")) {
                            putData2ErrorFunc(errorFunc,filter,false,errorNo);
                        }else
                        {
                            index ++;
                            putData2ErrorFunc(errorFunc,filter,true,errorNo);
                        }
                    }
                    if(index == filters.length)
                    {
                        putData2ErrorFunc(errorFunc,ERRORNOTMATCHED,false,errorNo);
                    }
                }
            }
            //关闭流
            reader.close();
            //写入相关文件
            writeData2File(errorFunc,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void putData2ErrorFunc(Map<String, Map<String, String>> errorFunc, String filter, boolean isNotFind,String errorNo) {
        Map<String, String> functErrors = errorFunc.get(filter);
        if(functErrors == null || functErrors.size() == 0)
        {
            functErrors = new HashMap<String, String>();
            functErrors.put(FUNCNUM,isNotFind ? "0" : "1");
            functErrors.put(FUNCNO,isNotFind ? "" : ","+errorNo);
        }else
        {
            if(!isNotFind)
            {
                Integer funcNum = Integer.parseInt(functErrors.get(FUNCNUM));
                String funcNo = functErrors.get(FUNCNO);
                functErrors.put(FUNCNUM,(funcNum+ 1)+"");
                functErrors.put(FUNCNO,funcNo+","+errorNo);
            }
        }
        errorFunc.put(filter,functErrors);
    }

    private static void writeData2File(Map<String, Map<String, String>> errorFunc,File out) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(out));
            Map<String, String> errorsNotMatched = errorFunc.get(ERRORNOTMATCHED);
            if(errorsNotMatched != null && errorsNotMatched.size() != 0)
            {
                write(writer,ERRORNOTMATCHED,errorsNotMatched);
            }
            Set<Map.Entry<String, Map<String, String>>> entrySet = errorFunc.entrySet();
            for(Map.Entry<String, Map<String, String>> map: entrySet)
            {
                String key = map.getKey();
                Map<String, String> value = map.getValue();
                if(ERRORNOTMATCHED.equals(key))
                {
                    continue;
                }
                write(writer,key,value);
            }
            //清楚缓存
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void write(BufferedWriter writer, String key, Map<String, String> value) throws IOException {
        String funcNum = String.valueOf(value.get(FUNCNUM));
        String funcNo = String.valueOf(value.get(FUNCNO));
        writer.write(key+": "+funcNum+funcNo);
        writer.newLine();
    }
}
