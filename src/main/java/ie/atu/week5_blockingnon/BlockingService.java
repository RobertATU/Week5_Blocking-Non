package ie.atu.week5_blockingnon;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlockingService {
    private final RestTemplate restTemplate;
    public BlockingService(RestTemplate restTemplate){this.restTemplate = restTemplate;}
    public String fetchDataBlocking() {
    try{Thread.sleep(2000);
    }catch (InterruptedException e){
        e.printStackTrace();
    }
    return restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos",String.class);
    }
}
