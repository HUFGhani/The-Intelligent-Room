TO SETUP REPO:
curl -O http://repo.mosquitto.org/debian/mosquitto-repo.gpg.key
sudo apt-key add mosquitto-repo.gpg.key
rm mosquitto-repo.gpg.key
cd /etc/apt/sources.list.d/
sudo curl -O http://repo.mosquitto.org/debian/mosquitto-repo.list
sudo apt-get update


TO INSTALL ON PI:
sudo apt-get install mosquitto mosquitto-clients

START MQTT BROKER: 
sudo /usr/sbin/mosquitto -c /etc/mosquitto/mosquitto.conf

STOP MQTT BROKER:
sudo /etc/init.d/mosquitto stop




CONFIG FILE:
Config file must be saved in '/etc/home-automation/ha.config' and contain:

{
	"houseID" : "testID123",
	"ip" : "255.255.255.255"
}


