package ie.atu.week5_blockingnon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class FeignController {
    private final FeignService feignService;
    public FeignController(FeignService feignService){
        this.feignService = feignService;
    }
    @GetMapping("/feign")
    public String getFeignData() throws Exception, InterruptedException{
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<TodoResponse>> futures = new ArrayList<>();

        for(int i = 0; i< 10;i++){
            CompletableFuture<TodoResponse> future = CompletableFuture.supplyAsync(() -> feignService.fetchData());
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allOf.get();
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime));

        return "Total execution time: " + (endTime - startTime) + " ms";
    }

    @GetMapping("/feignList")
    public TodoResponse getFeignDataList() throws Exception, InterruptedException {
        TodoResponse empty = new TodoResponse();
        List<TodoResponse> list = new ArrayList<>();
        for (int i = 190; i <= 199; i++) {
            list.add(feignService.fetchDataList(i));
        }
        for (TodoResponse lists : list) {
            if (lists.getId() == 196) {
                return lists;
            }

        }   return empty;

    }

}
