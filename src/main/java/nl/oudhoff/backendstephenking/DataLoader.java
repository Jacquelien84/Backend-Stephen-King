//package nl.oudhoff.backendstephenking;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import nl.oudhoff.backendstephenking.model.Book;
//import nl.oudhoff.backendstephenking.repository.BookRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static com.sun.imageio.plugins.jpeg.JPEG.version;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final ObjectMapper objectMapper;
//
//    public DataLoader(ObjectMapper objectMapper, BookRepository bookRepo) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<Book> books = new ArrayList<>();
//        JsonNode jsonNode;
//
//        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/_data/books.postman_collection.json")) {
//            if (inputStream == null) {
//                throw new RuntimeException("File not found: /_data/sk.postman.json");
//            }
//            jsonNode = objectMapper.readValue(inputStream, JsonNode.class);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read JSON data", e);
//        }
//
//        JsonNode edges = getEdges(jsonNode);
//        for (JsonNode edge : edges) {
//            books.add(createBookFromNode(edge));
//        }
//
//        System.out.println(books);
//    }
//
//    private Book createBookFromNode(JsonNode edge) {
//        JsonNode jsonNode = edge.get("book");
//        long id = jsonNode.get("id").asLong();
//        String title = jsonNode.get("title").asText();
//        String author = jsonNode.get("author").asText();
//        String originalTitle = jsonNode.get("original_title").asText();
//        long releaseDate = jsonNode.get("release_date").asLong();
//        String movieAdaptation = jsonNode.get("movie_adaptation").asText();
//        String description = jsonNode.get("description").asText();
//        String tags = extractTags(jsonNode);
//
//        return new Book(id, title, author, originalTitle, releaseDate, movieAdaptation, description, tags, version);
//    }
//
//    private String extractTags(JsonNode jsonNode) {
//        JsonNode tags = jsonNode.get("tags");
//        List<String> tagList = new ArrayList<>();
//        for (JsonNode tag : tags) {
//            tagList.add(tag.get("title").asText());
//        }
//        return String.join(",", tagList);
//    }
//
//    private JsonNode getEdges(JsonNode jsonNode) {
//        return Optional.ofNullable(jsonNode)
//                .map(j -> j.get("data"))
//                .map(j -> j.get("allBooks"))
//                .map(j -> j.get("edges")).orElseThrow(() -> new RuntimeException("Invalid JSON data"));
//    }
//}
