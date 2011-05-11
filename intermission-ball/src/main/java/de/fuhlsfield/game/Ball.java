/**
 * 
 */
package de.fuhlsfield.game;

/**
 * @author juergen
 * 
 */
public enum Ball {

	TISCHI_BALLI("TischiBalli"), // 
	BUNTI("Bunti"), // 
	BASKI("Baski"), // 
	SCHWAMMI("Schwammi"), // 
	FROESCHI("Fröschi"); 

	private String name;

	private Ball(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}