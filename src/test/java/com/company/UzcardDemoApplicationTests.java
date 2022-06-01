package com.company;

import com.company.dto.request.CardFilterDTO;
import com.company.repository.custom.CardCustomRepository;
import com.company.service.CardService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootTest

class UzcardDemoApplicationTests {



    @Autowired
    private com.company.repository.custom.CardCustomRepository cardCustomRepository ;

    @Test
    void contextLoads() {

      /*  String substring = "+998330030528".substring(4);

        System.out.println(substring);
    }*/







    }

}
