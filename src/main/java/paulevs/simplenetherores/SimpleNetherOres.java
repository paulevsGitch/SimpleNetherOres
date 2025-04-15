package paulevs.simplenetherores;

import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

public class SimpleNetherOres {
	public static final Namespace NAMESPACE = Namespace.of("simplenetherores");
	
	public static Identifier id(String name) {
		return NAMESPACE.id(name);
	}
}
