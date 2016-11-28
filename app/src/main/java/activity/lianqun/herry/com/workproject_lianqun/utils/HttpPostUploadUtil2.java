package activity.lianqun.herry.com.workproject_lianqun.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/26.
 */

public class HttpPostUploadUtil2 {
    /**
     * @param args
     */
    public static void main(String[] args) {

        String filepath1 = "C:\\Users\\Administrator\\Desktop\\11.jpg";
        String filepath2 = "C:\\Users\\Administrator\\Desktop\\22.png";

        String urlStr = "http://localhost:9090/SmartCommunity_API/repair/add";

        Map<String, String> textMap = new HashMap<String, String>();

        textMap.put("communityId", "1");
        textMap.put("content", "11111");
        textMap.put("repairUser", "2");
        textMap.put("type", "8");

        Map<String, List<String>> fileMap = new HashMap<String, List<String>>();

        List<String> list = new ArrayList<String>();
        list.add(filepath1);
        list.add(filepath2);
        fileMap.put("files", list);

        String ret = formUpload(urlStr, textMap, fileMap);

        System.out.println(ret);

    }

    /**
     * �ϴ�ͼƬ
     *
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @return
     */
    public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, List<String>> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; // boundary����requestͷ���ϴ��ļ����ݵķָ���
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null || inputValue == "") {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
                Iterator<String> iter = fileMap.keySet().iterator();
                while (iter.hasNext()) {

                    String key = iter.next();

                    List<String> files = fileMap.get(key);
                    if (null == files) {
                        continue;
                    }

                    for (String f : files) {

                        File file = new File(f);
                        String filename = file.getName();
						/*
						 * String contentType = new MimetypesFileTypeMap()
						 * .getContentType(file);
						 * System.out.println(contentType+"---"); if
						 * (filename.endsWith(".png")) { contentType =
						 * "image/png"; } if (contentType == null ||
						 * contentType.equals("")) { contentType =
						 * "application/octet-stream"; }
						 */

                        StringBuffer strBuf = new StringBuffer();
                        strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + filename
                                + "\"\r\n");
                        String contentType = "application/zip";
                        strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                        out.write(strBuf.toString().getBytes());

                        DataInputStream in = new DataInputStream(new FileInputStream(file));
                        int bytes = 0;
                        byte[] bufferOut = new byte[1024];
                        while ((bytes = in.read(bufferOut)) != -1) {
                            out.write(bufferOut, 0, bytes);
                        }
                        in.close();
                    }

                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // ��ȡ�������
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("����POST������?" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }
}