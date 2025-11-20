/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Finder {
    private String cleanedSource;

    public Finder(String filePath) throws IOException{
        StringBuilder sourceBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null){
                sourceBuilder.append(line).append("\n");
            }
        }
        String sourceCode = sourceBuilder.toString();
        cleanedSource = removeCommentsAndString(sourceCode);
    }

    private String removeCommentsAndString(String code){
        StringBuilder result = new StringBuilder();
        boolean inString = false;
        boolean inChar = false;
        boolean inSLComment = false;
        boolean inMLComment = false;

        for(int i = 0; i < code.length(); i++){
            char c = code.charAt(i);

            if(inSLComment){
                if(c == '\n'){
                    inSLComment = false;
                    result.append('\n');
                }
                continue;
            }
            if(inMLComment){
                if(c == '*' && i + 1 < code.length() && code.charAt(i + 1) == '/'){
                    inMLComment = false;
                    i++;
                } else if (c == '\n') {
                    result.append('\n');
                }
                continue;
            }
            if(inString){
                if (c == '\\' && i + 1 < code.length()){
                    result.append(' ');
                    i++;
                    result.append(' ');
                    continue;
                }
                if(c == '\"'){
                    inString = false;
                    result.append(' ');
                }else{
                    result.append(' ');
                }
                continue;
            }
            if (inChar){
                if (c == '\\' && i + 1 < code.length()){
                    result.append(' ');
                    i++;
                    result.append(' ');
                    continue;
                }
                if(c == '\''){
                    inChar = false;
                    result.append(' ');
                }else{
                    result.append(' ');
                }
                continue;
            }
            if (c == '\"'){
                inString = true;
                result.append(' ');
                continue;
            }
            if(c== '\''){
                inChar = true;
                result.append(' ');
                continue;
            }
            if(c == '/' && i + 1 < code.length()){
                char next = code.charAt(i + 1);
                if(next == '/'){
                    inSLComment = true;
                    i++;
                    continue;
                }
                if(next == '*'){
                    inMLComment = true;
                    i++;
                    continue;
                }
            }
            result.append(c);
        }
        return result.toString();
    }
    public int getIfCount(){
        int count = 0;
        String text = cleanedSource;
        String keyword = "if";
        int idx = text.indexOf(keyword);
        while(idx != -1){
            boolean leftBoundry = (idx == 0) || !Character.isJavaIdentifierPart(text.charAt(idx - 1));
            boolean rightBoundary = (idx + 2 >= text.length()) || !Character.isJavaIdentifierPart(text.charAt(idx + 2));
            if(leftBoundry && rightBoundary){
                count++;
            }
            idx = text.indexOf(keyword, idx + 1);
        }
        return count;
    }
    public int getStringCount(String searchText){
        int count = 0;
        int idx = cleanedSource.indexOf(searchText);
        while(idx != -1){
            count++;
            idx = cleanedSource.indexOf(searchText, idx + 1);
        }
        return count;
    }
}    
