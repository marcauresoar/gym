package com.gymproject.app.utils;

import java.math.BigInteger;
import java.util.Random;

public class HashUtils {
    public static String generateId(){
        return new BigInteger(16, new Random()).toString();
    }
}
