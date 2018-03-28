package techlab;


import java.io.InputStream;
import java.net.URLConnection;
import java.nio.ByteBuffer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component(value = "staticContentLoader" )
public class StaticContentLoader implements Processor, ResourceLoaderAware {

   private ResourceLoader resourceLoader;

    private String rootFolder = "classpath:static";

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location){
        return resourceLoader.getResource(location);
    }


    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();

        String relativepath = in.getHeader(Exchange.HTTP_PATH, String.class);

        if (relativepath.isEmpty() || relativepath.equals("/")) {
            relativepath = "/index.html";
        }

        Resource resource = this.getResource(rootFolder+relativepath);

        String mimeType;
        Message out = exchange.getOut();
        InputStream is = resource.getInputStream();
        mimeType = URLConnection.guessContentTypeFromStream(is);
        byte[] bytes = IOUtils.toByteArray(is);
        out.setBody(ByteBuffer.wrap(bytes));
        out.setHeader(Exchange.CONTENT_TYPE, mimeType);
        IOUtils.closeQuietly(is);
    }



}