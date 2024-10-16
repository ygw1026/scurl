package com.nhnacademy;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import com.nhnacademy.utils.ArgsParser;
import com.nhnacademy.utils.UrlParser;

public class Curl {
    public void run(String[] args){
        String url = args[args.length - 1];
        String[] argsExceptUrl = Arrays.copyOfRange(args, 0, args.length - 1);
        CommandLine commandLine = ArgsParser.makeCmdUsingArgs(argsExceptUrl);

        HttpRequest httpRequest = new HttpRequest(UrlParser.parse(url));
        for(Option option : commandLine.getOptions()) {
            httpRequest.setValueUsingParams(option);
        }
        List<String> result = httpRequest.serialize();

        System.out.println("=====HTTP Request Message START");
        for(String each : result) {
            System.out.println(each);
        }
        System.out.println("=====HTTP Request Message END");
    }
}
