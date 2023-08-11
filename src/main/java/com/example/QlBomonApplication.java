package com.example;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QlBomonApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Fibonaci
    public static void daySo(int n){
      int [] arr = new int[100];
        arr[0]= 0;
        System.out.println(arr[0]);
        arr[1]= 1;
        System.out.println(arr[1]);
        for (int i=2;i<n;i++) {
            arr[n] = arr[n - 1] + arr[n - 1 - 1];
            System.out.println(arr[n]);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(QlBomonApplication.class, args);
        daySo(5);
    }

}
