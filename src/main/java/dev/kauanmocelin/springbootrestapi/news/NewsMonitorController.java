package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.news.client.response.NewsApiResponse;
import dev.kauanmocelin.springbootrestapi.news.dto.KeywordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news-monitor")
@RequiredArgsConstructor
public class NewsMonitorController {

    private final NewsMonitorService newsMonitorService;

    @PostMapping("/keyword")
    @Operation(summary = "Register keyword for monitoring", description = "Register keyword for monitoring", tags = {"news-monitor"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful registered keyword"),
    })
    public ResponseEntity<Void> registerKeyword(@RequestBody @Valid final KeywordRequest request) {
        newsMonitorService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Fetch news from keyword", description = "Fetch ten news more popularity with a keyword", tags = {"news-monitor"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful fetched news from keyword"),
    })
    @GetMapping("/news/{keyword}")
    public ResponseEntity<NewsApiResponse> fetchNews(@PathVariable String keyword) {
        return ResponseEntity.ok(newsMonitorService.fetchNewsFromYesterday(keyword));
    }
}
