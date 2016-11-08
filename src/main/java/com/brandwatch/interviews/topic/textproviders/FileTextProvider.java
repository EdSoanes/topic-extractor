package com.brandwatch.interviews.topic.textproviders;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class FileTextProvider implements TextProvider {

    public String readText(String fileName) throws IOException {

        BufferedReader reader = null;

        try {
            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
        finally {
            if (reader != null)
                reader.close();
        }
    }

    public String[] readTextLines(String fileName) throws IOException {

        BufferedReader reader = null;

        try {
            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            ArrayList<String> res = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line);
            }

            String[] resArray = new String[res.size()];
            return res.toArray(resArray);
        }
        finally {
            if (reader != null)
                reader.close();
        }
    }
}
