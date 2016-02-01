package com.tudou.open.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 初始化工具类（用于程序初始化）
 * 
 * @author myao
 */
public class Initers {

	private static final Initers it = new Initers();
	private Map<KV, AtomicBoolean> initers = new LinkedHashMap<KV, AtomicBoolean>();

	private Initers() {
		Conf.getConfig();
		addIniter(Log4j.getIt());
	}

	public static Initers getIt() {
		return it;
	}

	public void addIniter(Initable initer) {
		initers.put(new KV(initer, null), new AtomicBoolean(false));
	}

	public void addIniter(Initable initer, Object param) {
		initers.put(new KV(initer, param), new AtomicBoolean(false));
	}

	public void doInit(Initable initer) throws Exception {
		initer.init(null);
		initers.put(new KV(initer, null), new AtomicBoolean(true));
	}

	public void doInit(Initable initer, Object param) throws Exception {
		initer.init(param);
		initers.put(new KV(initer, param), new AtomicBoolean(true));
	}

	public void doInitAll() throws Exception {
		for (Entry<KV, AtomicBoolean> entry : initers.entrySet()) {
			if (entry.getValue().get()) {
				continue;
			}
			entry.getKey().initer.init(entry.getKey().param);
			entry.getValue().set(true);
		}
	}

	private class KV {

		private Initable initer;
		private Object param;

		KV(Initable initer, Object param) {
			super();
			this.initer = initer;
			this.param = param;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((initer == null) ? 0 : initer.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final KV other = (KV) obj;
			if (initer == null) {
				if (other.initer != null)
					return false;
			} else if (!initer.equals(other.initer))
				return false;
			return true;
		}

	}

}
