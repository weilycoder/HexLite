package virtuoel.pehkui.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public class I18nUtils
{
	public static final Object[] EMPTY_VARARGS = new Object[0];
	
	private static final boolean RESOURCE_LOADER_LOADED = ModLoaderUtils.isModLoaded("fabric-resource-loader-v0");
	
	public static Component translate(final String unlocalized, final String defaultLocalized)
	{
		return translate(unlocalized, defaultLocalized, EMPTY_VARARGS);
	}
	
	private static final Function<String, Object> LITERAL = LiteralContents::new;
	private static final Constructor<TranslatableContents> TRANSLATABLE = ReflectionUtils.getConstructor(Optional.of(TranslatableContents.class), String.class, Object[].class).orElse(null);
	
	public static Component translate(final String unlocalized, final String defaultLocalized, final Object... args)
	{
		if (RESOURCE_LOADER_LOADED)
		{
			if (VersionUtils.MINOR < 19)
			{
				try
				{
					return (Component) TRANSLATABLE.newInstance(unlocalized, args);
				}
				catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					
				}
			}
			
			return Component.translatable(unlocalized, args);
		}
		
		if (VersionUtils.MINOR < 19)
		{
			return (Component) LITERAL.apply(String.format(defaultLocalized, args));
		}
		
		return literal(defaultLocalized, args);
	}
	
	public static Component literal(final String text, final Object... args)
	{
		if (VersionUtils.MINOR < 19)
		{
			return (Component) LITERAL.apply(String.format(text, args));
		}
		
		return Component.literal(String.format(text, args));
	}
}
