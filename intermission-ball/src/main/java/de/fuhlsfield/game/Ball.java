/**
 * 
 */
package de.fuhlsfield.game;

/**
 * @author juergen
 * 
 */
public enum Ball {

	TISCHI_BALLI("TischiBalli", 4, true), // 
	TISCHI_BALLI_FAIL("-TischiBalli", 0, false), //
	BUNTI("Bunti", 4, true), // 
	BUNTI_FAIL("-Bunti", 0, false), //
	BASKI("Baski", 4, true), // 
	BASKI_FAIL("-Baski", 0, false), //
	SCHWAMMI("Schwammi", 4, true), // 
	SCHWAMMI_FAIL("-Schwammi", 0, false), //
	FROESCHI("Fröschi", 4, true), // 
	FROESCHI_FAIL("-Fröschi", 0, false), //
	NONE(null, 0, false), //
	;

	private String name;
	private int points;
	private boolean valid;

	private Ball(String name, int points, boolean valid) {
		this.name = name;
		this.points = points;
		this.valid = valid;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public boolean isValid() {
		return valid;
	}

}
