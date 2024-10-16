package com.nhnacademy.utils;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.nhnacademy.domain.MyOption;

public class ArgsParser {
    public static CommandLine makeCmdUsingArgs(String[] args){
        try{
            DefaultParser parser = new DefaultParser();
            Options options = makeOptionsUsingValues();
            return parser.parse(options, args);
        }catch(ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static Options makeOptionsUsingValues() {
        Options options = new Options();
        Arrays.stream(MyOption.values())
            .map(MyOption::getOption)
            .forEach(options::addOption);

            return options;
    }
}
