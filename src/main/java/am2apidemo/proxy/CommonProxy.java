package am2apidemo.proxy;

import am2apidemo.spell.SpellList;

public class CommonProxy {
	
	public SpellList spellList;
	
	public void init(){
		spellList = new SpellList();
		spellList.init();
		spellList.register();
	}
	
	public void registerEventListeners(){
		
	}
	
	public void registerPostEventListeners(){
		
	}
}
