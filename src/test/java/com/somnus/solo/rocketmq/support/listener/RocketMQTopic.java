package com.somnus.solo.rocketmq.support.listener;

public class RocketMQTopic {
    
    private String group;
    
    private String instance;

    private String topic;
    
    private String tags;

    public RocketMQTopic(String topic, String group, String instance, String tags) {
        super();
        this.group = group;
        this.instance = instance;
        this.topic = topic;
        this.tags = tags;
    }

    public RocketMQTopic() {
        super();
    }

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.topic == null ? 0 : this.topic.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        RocketMQTopic other = (RocketMQTopic) obj;
        if (this.topic == null) {
            if (other.topic != null) {
                return false;
            }
        }
        else if (!this.topic.equals(other.topic)) {
            return false;
        }
        return true;
    }

    public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

    public String getTopic() {
        return this.topic;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
