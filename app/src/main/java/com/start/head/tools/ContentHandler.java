package com.start.head.tools;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder id,name,version,strSum;

    @Override
    public void startDocument() throws SAXException {
        id=new StringBuilder();
        name=new StringBuilder();
        version=new StringBuilder();
        strSum=new StringBuilder();
        //开始XML解析的时候调用
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName=localName;
        //开始解析某个节点的时候调用
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //根据当前的节点名判断将内容添加到哪一个StringBuilder对象中
        if ("id".equals(nodeName)){
            id.append(ch,start,length);
        }else if ("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if ("version".equals(nodeName)){
            version.append(ch,start,length);
        }
        //获取节点中的内容的时候调用
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)){
            String idTrim=id.toString().trim();
            String nameTrim=name.toString().trim();
            String versionTrim=version.toString().trim();
            strSum.append("id is").append(idTrim).append("name is").append(nameTrim).append("version is").append(versionTrim).append("\n");
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
        //完成解析某个节点的时候调用
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        //完成XML解析的时候调用
    }
    @NotNull
    public String getStrSum(){
        return strSum.toString();
    }
}
