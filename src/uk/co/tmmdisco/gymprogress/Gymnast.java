package uk.co.tmmdisco.gymprogress;



/*
 * Data object that holds all of our information.
 */
public class Gymnast {
	
	private String name;
	private String age;
	private String disciplineLink;
	private String level;
	private String disciplineImg;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getLevel(){
		return level;
	}
	public void setLevel(String level){
		this.level = level;
	}
	public String getDisciplineLink() {
		return disciplineLink;
	}
	public void setDisciplineLink(String disciplineLink) {
		this.disciplineLink = disciplineLink;
	}
	public String getDisciplineImg() {
		return disciplineImg;
	}
	public void setDisciplineImg(String disciplineImg) {
		this.disciplineImg = disciplineImg;
	}

	@Override
	public String toString() {
		return "gymnast [name=" + name + ", age=" + age + ", level=" + level +", discipline="
				+ disciplineLink +"disciplineImg"+ disciplineImg + "]";
	}
}
