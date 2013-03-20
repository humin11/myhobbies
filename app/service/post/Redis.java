package service.post;

import org.codehaus.jackson.JsonNode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class Redis {

    private static JedisPool pool;

    public final static String TO_PERSON = "person_";

    public final static String TO_GROUP = "group_";

    public static Jedis getRedis(){
        if(pool == null)
            pool = new JedisPool("localhost",6379);
        return pool.getResource();
    }

    public static void returnPool(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void publish(String channel,JsonNode data){
        Jedis jedis = getRedis();
        jedis.publish(channel, data.toString());
        returnPool(jedis);
    }

    public static void subscribe(JedisPubSub receiver,String channel){
        Jedis jedis = getRedis();
        jedis.subscribe(receiver, channel);
        returnPool(jedis);
    }

    public static String personChannel(Long id){
        return TO_PERSON+id;
    }

    public static String groupChannel(Long id){
        return TO_GROUP+id;
    }

    public static void publish2Person(Long id,JsonNode data){
        publish(personChannel(id),data);
    }

    public static void publish2Group(Long id,JsonNode data){
        publish(groupChannel(id),data);
    }

}
