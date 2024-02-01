package ru.projects.calories.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils
{
	private static final ObjectMapper mapper = new ObjectMapper();

	/*

	// this method to add cookie product in map
	public static String toCookie(Product product, Map<Product, Long> map, boolean removed, boolean all)
	{
		if (removed)
		{
			if (map.containsKey(product))
			{
				if (map.get(product) == 1 || all)
				{
					map.remove(product);
				}
				else
				{
					map.replace(product, map.get(product), map.get(product) - 1);
				}
			}
		}
		else
		{
			if (map.containsKey(product))
			{
				map.replace(product, map.get(product), map.get(product) + 1);
			}
			else
			{
				map.put(product, 1L);
			}
		}

		return getString(map);
	}

	public static String toCookie(Product product, Map<Product, Long> map, boolean removed, Long amount)
	{
		if (removed)
		{
			if (map.containsKey(product))
			{
				if (map.get(product) == 1)
				{
					map.remove(product);
				}
				else
				{
					map.replace(product, map.get(product), map.get(product) - amount);
				}
			}
		}
		else
		{
			if (map.containsKey(product))
			{
				map.replace(product, map.get(product), map.get(product) + amount);
			}
			else
			{
				map.put(product, amount);
			}
		}

		return getString(map);
	}

	public static String toCookie(Product product, Map<Product, Long> map, Long amount)
	{
		if (map.containsKey(product))
		{
			map.replace(product, map.get(product), amount);
		}
		else
		{
			map.put(product, amount);
		}

		return getString(map);
	}

	this method convert map to string
	private static String getString(Map<Product, Long> map)
	{
		List<CartObject> cartObject = new ArrayList<>();

		map.forEach((key, value) ->
		{
			cartObject.add(new CartObject(key.getId(), value));
		});

		String items = "";
		try
		{
			items = mapper.writeValueAsString(cartObject);
		}
		catch (JsonProcessingException ignored)
		{
		}

		return URLEncoder.encode("items=" + items);
	}

	this method get map on product in cookie
	public static Map<Product, Long> fromCookie(String cookie, ProductService productService)
	{
		Map<Product, Long> map = new HashMap<>();

		List<CartObject> cartObject = new ArrayList<>();

		cookie = URLDecoder.decode(cookie);
		if (cookie.length() > 6)
		{
			cookie = cookie.substring(6);
		}

		try
		{
			cartObject = mapper.readValue(cookie, new TypeReference<List<CartObject>>()
			{
			});
		}
		catch (IOException ignored)
		{
		}

		cartObject.forEach(el ->
		{
			Product product = productService.findById(el.getId());
			map.put(product, el.getAmount());
		});

		return map;
	}*/
}
