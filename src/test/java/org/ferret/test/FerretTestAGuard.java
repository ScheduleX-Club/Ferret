package org.ferret.test;


import org.ferret.Parameters;
import org.ferret.annotations.Guard;
import org.ferret.annotations.type.AdviceType;
import org.springframework.stereotype.Component;

@Component
public class FerretTestAGuard {



    @Guard(AdviceType.Before)
    public void before(Parameters parameters){
        System.out.println(parameters);
        System.out.println("before");

    }

    @Guard(AdviceType.After)
    public void afer(Parameters parameters){

        System.out.println(parameters);
        System.out.println("after");
    }


    @Guard(AdviceType.Error)
    public void error(Parameters parameters){

        System.out.println(parameters);
        System.out.println("error");
    }






}
