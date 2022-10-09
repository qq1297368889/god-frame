package gzb.tools.cache;

import gzb.tools.Tools;
import gzb.tools.log.LogImpl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class GzbCacheRedis implements GzbCache {

    static gzb.tools.log.Log Log=new LogImpl(GzbCacheRedis.class);
    public static void main(String[] args) {
        GzbCacheRedis g = new GzbCacheRedis();
        g.set("k1", "123");
        Log.i(g.get("k1"));
    }

    private static JedisPool jedisPool = null;

    static {
        try {
            String id = "01";
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            String ip = Tools.configGetString("gzb.redis." + id + ".ip", "127.0.0.1");
            Integer port = Tools.configGetInteger("gzb.redis." + id + ".port", "6379");
            String pwd = Tools.configGetString("gzb.redis." + id + ".pwd", "");
            Integer threadMax = Tools.configGetInteger("gzb.redis." + id + ".threadMax", "30");
            Integer freeThreadMax = Tools.configGetInteger("gzb.redis." + id + ".freeThreadMax", "10");
            jedisPoolConfig.setMaxTotal(threadMax);
            jedisPoolConfig.setMaxIdle(freeThreadMax);
            if (pwd == null || pwd.length() < 1) {
                jedisPool = new JedisPool(jedisPoolConfig,
                        ip,
                        port,
                        10000);
            } else {
                jedisPool = new JedisPool(jedisPoolConfig,
                        ip,
                        port,
                        10000, pwd);
            }
            System.out.println("redis,初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis,初始化失败" + e);
        }

    }

    public Long getIncr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String get(String key, long mm) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String val = jedis.get(key);
            if (val == null) {
                return null;
            }
            jedis.expire(key, (int) mm);
            return val;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            val = val == null ? "" : val;
            jedis.set(key, val.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String val, long mm) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            val = val == null ? "" : val;
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
            jedis.set(key, val.toString(), "XX", "EX", mm);
            jedis.set(key, val.toString(), "NX", "EX", mm);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public String del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String obj = jedis.get(key);
            jedis.del(key);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


}