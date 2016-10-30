package com.intive.controller;

import com.google.gson.Gson;
import com.intive.service.DataService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class BaseController {

    private static final String VIEW_INDEX = "index";
    private static final String VIEW_TEST = "test";
    private final Gson gson;
    private final Configuration cfg;

    private DataService service;

    public BaseController() {
        service = new DataService();
        gson = new Gson();

        cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setClassForTemplateLoading(getClass(), "/templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
    	System.out.println("index");

        return VIEW_INDEX;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testPage() {
        System.out.println("test");
    	return VIEW_TEST;
    }

    @RequestMapping("/html")
    public void getHtml(HttpServletResponse response) throws IOException, URISyntaxException {
        String html = service.getHtml();
//        String html = "<html><body><h1>HelloWorld!!</h1></body></html>";
        String data = service.getJson();

        System.out.println("html = " + html);
        System.out.println("json = " + data);

        Map model = new HashMap();
        model.put("html", html);
        model.put("data", data);
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:3000/generate-pdf-from-data-and-dot-template");
        RequestCallback cb = clientHttpRequest -> {
            Writer nodeWriter = new OutputStreamWriter(clientHttpRequest.getBody());
            nodeWriter.write(gson.toJson(model));
            nodeWriter.flush();
            clientHttpRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        };
        ResourceReader reader = new StreamResourceReader(response);
        restTemplate.execute(url, HttpMethod.POST, cb, new StreamResponseExtractor(reader));
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public String getPdf() {
        System.out.println("test");
    	return VIEW_TEST;
    }

    @RequestMapping("/generate")
    public void generate(HttpServletResponse response) throws IOException, TemplateException, URISyntaxException {
        try {
            Map data = new HashMap();
            ChartValues values = new ChartValues();
            generateRandomValues(values.getVals());
            data.put("values", values);

            Template temp = cfg.getTemplate("test.ftl");
            // Generowanie pliku z html
/*			Writer file = new FileWriter (new File("test.html"));
			temp.process(data, file);
			file.flush();
			file.close();

			System.out.println("Wygenerowano plik test.html");
*/
            RestTemplate restTemplate = new RestTemplate();
            URI url = new URI("http://localhost:3000/generate-pdf-from-html");

            RequestCallback cb = clientHttpRequest -> {
                Writer writer = new OutputStreamWriter(clientHttpRequest.getBody());
                clientHttpRequest.getHeaders().setContentType(MediaType.TEXT_HTML);
                try {
                    temp.process(data, writer);
                } catch (TemplateException e) {
                    System.err.println("Something went terribly wrong.");
                    e.printStackTrace();
                }
            };

            ResourceReader reader = new StreamResourceReader(response);
            restTemplate.execute(url, HttpMethod.POST, cb, new StreamResponseExtractor(reader));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateRandomValues(List<String> vals) {
        for (int i=0; i<getRandomNumberInRange(); i++) {
            vals.add(Integer.toString(getRandomNumberInRange()));
        }
    }

    private static int getRandomNumberInRange() {
        Random r = new Random();
        return r.nextInt((200 - 0) + 1) + 0;
    }
}