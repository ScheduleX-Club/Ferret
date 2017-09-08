package org.ferret.test;


import org.ferret.annotations.Prisoner;
import org.springframework.stereotype.Service;

@Service
public class FerretTestA {



    @Prisoner
    public String executeMethod(String string){


        System.out.println("executeMethod 执行");
        if(1 == 1 ){
            //throw new RuntimeException("老子不开心拉");
        }

        return "返回值";
    }




}
