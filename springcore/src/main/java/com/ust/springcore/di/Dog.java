package com.ust.springcore.di;

import org.springframework.stereotype.Component;

@Component("dog")
public class Dog implements Animal{
@Override
public void makeSound() {
System.out.println("bowwwwwwwwwwwwwwwwwwwww");	
}
}
