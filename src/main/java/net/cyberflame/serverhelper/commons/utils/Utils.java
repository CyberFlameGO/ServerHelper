package net.cyberflame.serverhelper.commons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.concurrent.atomic.AtomicReference;

import static net.kyori.adventure.text.Component.text;

public class Utils
{
	// If I need to use more components than just text, I'll make this method accept "Component..." or whatever the
	// actual name of it is, so it can accept multiple components.
	@SuppressWarnings("UnnecessaryLocalVariable")
	public static Component component(String string)
	{
		AtomicReference<TextComponent> textComponent =
				new AtomicReference<>(text().append(LegacyComponentSerializer.legacy('&').deserialize(string)).build());
		Component adventureComponent = textComponent.get();
		return adventureComponent;
	}
}
