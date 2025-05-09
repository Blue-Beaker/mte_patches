package io.bluebeaker.mtepatches.projectred;

import mrtjp.projectred.transportation.PayloadPipePart;
import mrtjp.projectred.transportation.PressurePayload;
import mrtjp.projectred.transportation.TPressureTube;
import scala.collection.Iterator;

public class Utils {
    public static int countWanderingItems(TPressureTube tube){
        Iterator<PressurePayload> it = ((PayloadPipePart<PressurePayload>)tube).itemFlow().it();
        int count=0;
        while (it.hasNext()){
            PressurePayload payload = it.next();
            count=count+1;
        }
        return count;
    }
}
