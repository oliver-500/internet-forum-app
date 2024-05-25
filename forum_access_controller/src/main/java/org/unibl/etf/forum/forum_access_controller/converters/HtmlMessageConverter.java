package org.unibl.etf.forum.forum_access_controller.converters;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class HtmlMessageConverter extends StringHttpMessageConverter implements GithubOAuth2Response {
    public HtmlMessageConverter() {
        super(StandardCharsets.UTF_8);
    }

    @Override
    public boolean canRead(Class<?> clazz, org.springframework.http.MediaType mediaType) {
        return clazz == String.class && mediaType.includes(org.springframework.http.MediaType.TEXT_HTML);
    }

    @Override
    public boolean canWrite(Class<?> clazz, org.springframework.http.MediaType mediaType) {
        return false; // This converter is only for reading
    }

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(org.springframework.http.MediaType.TEXT_HTML);
    }
}
