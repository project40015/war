package decimatepurge.game.module.modules.objects;

import java.util.ArrayList;
import java.util.List;

public enum GearItemType {

	HELMET,
	CHESTPLATE,
	LEGGINGS,
	BOOTS,
	WEAPON_MAIN,
	WEAPON_BOW,
	MISC;
	
	public static List<GearItemType> getTypeList(){
		List<GearItemType> list = new ArrayList<>();
		for(GearItemType type : GearItemType.values()){
			list.add(type);
		}
		return list;
	}
	
}
