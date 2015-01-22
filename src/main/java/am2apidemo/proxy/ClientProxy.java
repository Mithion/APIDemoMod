package am2apidemo.proxy;

import am2apidemo.ModCompendiumEventHandler;
import am2apidemo.ModIconEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerEventListeners() {
		super.registerEventListeners();
		MinecraftForge.EVENT_BUS.register(new ModCompendiumEventHandler());
	}
	
	@Override
	public void registerPostEventListeners() {
		super.registerPostEventListeners();
		MinecraftForge.EVENT_BUS.register(new ModIconEventHandler());
	}
}
