package cn.yr.netty.files.directory2.client;

public class Message {
	private int nameLength;
	private String name;
	private long contentLength;
	//内容过大不能放在这里面
	public int getNameLength() {
		return nameLength;
	}
	public void setNameLength(int nameLength) {
		this.nameLength = nameLength;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
