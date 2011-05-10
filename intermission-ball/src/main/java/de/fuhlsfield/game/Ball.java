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
	TISCHI_BALLI_FAIL("-TischiBalli"), //
	BUNTI("Bunti"), // 
	BUNTI_FAIL("-Bunti"), //
	BASKI("Baski"), // 
	BASKI_FAIL("-Baski"), //
	SCHWAMMI("Schwammi"), // 
	SCHWAMMI_FAIL("-Schwammi"), //
	FROESCHI("Fröschi"), // 
	FROESCHI_FAIL("-Fröschi"); //

	private String name;

	private Ball(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}