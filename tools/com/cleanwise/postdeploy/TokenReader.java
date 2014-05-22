package com.cleanwise.useractivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TokenReader {
    private List<Token> _tokens;
    private Map<Token.Type,List<Pair<String,String>>> _parsedData;

    public TokenReader(List<Token> tokens) {
        _tokens = tokens;
        _parsedData = new Hashtable<Token.Type, List<Pair<String, String>>>();
    }
    
    public TokenReader() {
        _parsedData = new Hashtable<Token.Type, List<Pair<String, String>>>();
    }

    public void processLine(String line, String filePath) {
        if (_tokens != null) {
            List<Pair<String,String>> parsedLines;
            String userName;
            for (Token token : _tokens) {
                token.processLine(line);
                if (token.isFilled()) {
                    userName = token.parseValue(Token.USER_REGEX);
                    if (userName != null) {
                        parsedLines = _parsedData.get(token.getType());
                        if (parsedLines == null) {
                            parsedLines = new ArrayList<Pair<String, String>>();
                        }
                        parsedLines.add(new Pair<String, String>(token.getValue(), filePath));
                        _parsedData.put(token.getType(), parsedLines);
                    }
                    token.clear();
                    break;
                }
            }
        }
    }
    
    public void addToken (Token token) {
        if (_tokens == null) {
            _tokens = new ArrayList<Token>();
        }
        _tokens.add(token);
    }

    public Map<Token.Type,List<Pair<String,String>>> getParsedData() {
        return _parsedData;
    }
}