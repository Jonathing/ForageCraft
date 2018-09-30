package com.theishiopian.foragecraft.json;

public class ForageTable
{
	private String name;
	private int age;

	//test methods from a gson tutorial. you gotta start somewhere.
	
	public ForageTable()
	{

	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String toString()
	{
		return "Student [ name: " + name + ", age: " + age + " ]";
	}
}
