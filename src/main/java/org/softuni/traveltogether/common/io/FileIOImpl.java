package org.softuni.traveltogether.common.io;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class FileIOImpl implements FileIO {
    @Override
    public String read(String file) throws IOException {
        return StreamUtils.copyToString(
                this.getClass().getResourceAsStream(file),
                Charset.defaultCharset());
    }

    @Override
    public void write(String fileContent, String file) throws IOException {

    }
}
