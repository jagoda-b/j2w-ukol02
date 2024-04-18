package cz.czechitas.java2webapps.ukol2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class QuoteImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteImageService.class);
    private static final String QUOTES_SRC = "citaty.txt";
    private static final String IMAGES_SRC = "src/main/resources/static/images";
    private final List<String> quotes;
    private final List<String> images;
    private final Random random = new Random();

    public QuoteImageService() throws IOException {
        quotes = readAllLines();
        images = readAllImages();
    }

    public String getRandomQuote() {
        return quotes.get(random.nextInt(quotes.size()));
    }

    public String getRandomImage() {
        String imagePath = images.get(random.nextInt(images.size()));
        return "/images/" + Paths.get(imagePath).getFileName().toString();
    }

    private List<String> readAllLines() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(QUOTES_SRC)) {
            if (inputStream == null) throw new AssertionError();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().toList();
            }
        }
    }

   private List<String> readAllImages() {
    try (Stream<Path> paths = Files.walk(Paths.get(IMAGES_SRC))) {
        return paths
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .toList();
    } catch (IOException e) {
        LOGGER.error("Could not read images from directory");
        return Collections.emptyList();
    }
}
}