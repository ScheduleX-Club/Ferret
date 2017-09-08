package org.ferret;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ferret.annotations.type.AdviceType;

@Getter
@Setter
@ToString
public class Parameters {

    private AdviceType adviceType;

    private Object[]  args;

    private Object result;

    private Throwable throwable;


}
