package de.fuhlsfield.game;

/**
 * @author juergen
 */
public enum Ball {

	TISCHI_BALLI("TischiBalli"), // 
	BUNTI("Bunti"), // 
	BASKI("Baski"), // 
	SCHWAMMI("Schwammi"), // 
	FROESCHI("Fröschi"), //
	NORMALI("Normali"), //
	SCHRAEGI("Schrägi"), //
	FLUMMI("Flummi");

	public static Ball getBallByName(String name) {
		for (Ball ball : values()) {
			if (ball.name.equals(name)) {
				return ball;
			}
		}
		throw new IllegalArgumentException("No ball with name " + name + " defined!");
	}

	private String name;

	private Ball(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}