package gzb.api;

import gzb.tools.groovy.Request;

//TestAction/test01
@Request(url="///test//",contentType="application/json;charset=UTF-8",crossDomain=false)
public class TestAction {
    String test01(){
        return "111";
    }
}
