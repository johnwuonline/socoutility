package com.soco.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.soco.log.Log;

public class XmlConfig {

    private String _file = "";
    private static final String _SERVER_NODE = "Server";
    private String _server_name = "";
    private String _connection_node = "";
    private static final String _PORT_NODE = "Port";
    private int _port = 0;
    private static final String _DBURL_NODE = "DBUrl";
    private String _DBUrl = "";
    private static final String _DB_NODE = "DB";
    private String _db = "";
    private static final String _USER_NODE = "User";
    private String _User ="";
    private static final String _Password_NODE = "Password";
    private String _password = "";
    
    public XmlConfig(String xml_file){
        this._file = xml_file;
    }
    
    public XmlConfig setServerName(String name){
        this._server_name = name;
        return this;
    }
    
    public XmlConfig setConnectionNode(String node){
        this._connection_node = node;
        return this;
    }
    
    public int getPort(){
        return this._port;
    }
    
    public String getDBUrl(){
        return this._DBUrl;
    }
    
    public String getDB(){
        return this._db;
    }
    
    public String getUser(){
        return this._User;
    }
    
    public String getPassword(){
        return this._password;
    }
    
    public XmlConfig parse() {
        File inFile = null;
        XMLStreamReader readerXML = null;
        try {
            boolean isWantedServer = false;
            boolean isWantedConnect = false;
            String value = "";
            inFile = new File(this._file);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            readerXML = factory.createXMLStreamReader(
                        new InputStreamReader(new FileInputStream(inFile)));
            while(readerXML.hasNext()){
                int event = readerXML.next();
                switch(event){
                case XMLStreamConstants.START_ELEMENT:
                    //start element
                    if(!this._server_name.equals("")
                    && readerXML.getLocalName().equals(_SERVER_NODE)
                    && readerXML.getAttributeValue(0).equals(this._server_name)){
                        isWantedServer = true;
                        //Log.log("is wanted server:"+this._server_name);
                    } 
                    if(!this._connection_node.equals("")
                    && readerXML.getLocalName().equals(this._connection_node)){
                        isWantedConnect = true;
                        //Log.log("is wanted connect:"+this._connection_node);
                    } 
                    break;
                case XMLStreamConstants.CHARACTERS:
                    //characters
                    value = readerXML.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    // end element
                    if(isWantedServer && isWantedConnect){ 
                        if(readerXML.getLocalName().equals(_PORT_NODE)){
                            this._port = Integer.parseInt(value);
                        } else if (readerXML.getLocalName().equals(_DBURL_NODE)){
                            this._DBUrl = value;
                        } else if (readerXML.getLocalName().equals(_DB_NODE)){
                            this._db = value;
                        } else if (readerXML.getLocalName().equals(_USER_NODE)){
                            this._User = value;
                        } else if (readerXML.getLocalName().equals(_Password_NODE)){
                            this._password = value;
                        }
                        //Log.log(readerXML.getLocalName()+":"+value);
                    }
                    //
                    if(readerXML.getLocalName().equals(_SERVER_NODE)){
                        isWantedServer = false;
                    }
                    if(readerXML.getLocalName().equals(this._connection_node)){
                        isWantedConnect = false;
                    }
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    //start document
                    Log.log("Start document");
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Log.log(ex.getMessage());
        } catch (XMLStreamException ex){
            Log.log(ex.getMessage());
        } finally {
            if(readerXML != null){
                try {
                    readerXML.close();
                } catch (XMLStreamException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return this;
    }
    
}
