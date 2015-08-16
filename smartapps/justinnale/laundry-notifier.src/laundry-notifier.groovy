/**
 *  Laundry Notifier vA
 *
 *  Copyright 2015 Justin Nale
 *  Based heavily on Laundry Monitor by Brandon Miller
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
import groovy.time.* 
 
definition(
    name: "Laundry Notifier",
    namespace: "JustinNale",
    author: "Justin Nale",
    description: "This application is a (further) modification of the SmartThings Laundry Monitor SmartApp.  This allows for a janky user selection and instead of using a vibration sensor, this utilizes Power (Wattage) draw from an Aeon Smart Energy Meter.",
    category: "Convenience",
    iconUrl: "http://www.clker.com/cliparts/6/b/d/5/1207431803659474302laundry%20laundomat%20black.svg.med.png",
    iconX2Url: "http://www.clker.com/cliparts/6/b/d/5/1207431803659474302laundry%20laundomat%20black.svg.med.png"
    )


preferences {
	section("Tell me when this washer has stopped..."){
		input "sensor1", "capability.powerMeter"
	}
    
    // Dryer def will go here

        section("User 1"){
		input "myswitch-User1", "capability.switch"
        input "name-User1", "text", title: "Name?"
        input "phone-User1", "phone", title: "Send a text message?", required: false
	}
    
    	section("User 2"){
		input "myswitch-User2", "capability.switch", required: false
        input "name-User2", "text", title: "Name?", required: false
        input "phone-User2", "phone", title: "Send a text message?", required: false
	}
    
    	section("User 3"){
		input "myswitch-User3", "capability.switch", required: false
        input "name-User3", "text", title: "Name?", required: false
        input "phone-User3", "phone", title: "Send a text message?", required: false
	}    
    
    	section("User 4"){
		input "myswitch-User4", "capability.switch", required: false
        input "name-User4", "text", title: "Name?", required: false
        input "phone-User4", "phone", title: "Send a text message?", required: false
	}    

    	section("User 5"){
		input "myswitch-User5", "capability.switch", required: false
        input "name-User5", "text", title: "Name?", required: false
        input "phone-User5", "phone", title: "Send a text message?", required: false
	}
    
    	section("User 6"){
		input "myswitch-User6", "capability.switch", required: false
        input "name-User6", "text", title: "Name?", required: false
        input "phone-User6", "phone", title: "Send a text message?", required: false
	}    
    }
    
    section("OLD_Notifications") {
		input "sendPushMessage", "bool", title: "Push Notifications?"
		input "phone", "phone", title: "Send a text message?", required: false
	}

	section("System Variables"){
    	//input "sendPushMessage", "bool", title: "Push Notifications?"
    	input "minimumWattage", "decimal", title: "Minimum running wattage", required: false, defaultValue: 50
        //input "message", "text", title: "Notification message", description: "Washer is done!", required: true
	}
	
    // JN - I dont know what this is
	//section ("Additionally", hidden: hideOptionsSection(), hideable: true) {
	//    input "phone", "phone", title: "Send a text message to:", required: false
	//    input "speech", "capability.speechSynthesis", title:"Speak message via: ", multiple: true, required: false
	//}


def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(sensor1, "power", powerInputHandler)
}

def powerInputHandler(evt) {
	def latestPower = sensor1.currentValue("power")
    log.trace "Power: ${latestPower}W"
    
    if (!atomicState.isRunning && latestPower > minimumWattage) {
    	atomicState.isRunning = true
		atomicState.startedAt = now()
        atomicState.stoppedAt = null
        log.trace "Cycle started."
        sendpush "Cycle started."
    } else if (atomicState.isRunning && latestPower < minimumWattage) {
    	atomicState.isRunning = false
        atomicState.stoppedAt = now()  
        log.debug "startedAt: ${atomicState.startedAt}, stoppedAt: ${atomicState.stoppedAt}"                    



		if (myswitch-User1){
        	if (myswitch-User1.currentSwitch == "on"){
            	def message = "${name-user1.value} - Your wash is done!"
            	sendsms phone-User1, message
            }
		}
        
		if (myswitch-User2){
        	if (myswitch-User2.currentSwitch == "on"){
            	def message = "${name-user2.value} - Your wash is done!"
            	sendsms phone-User2, message
            }
        } 
        
		if (myswitch-User3){
        	if (myswitch-User3.currentSwitch == "on"){
            	def message = "${name-user3.value} - Your wash is done!"
            	sendsms phone-User3, message
            }
        }
        
		if (myswitch-User4){
        	if (myswitch-User4.currentSwitch == "on"){
            	def message = "${name-user4.value} - Your wash is done!"
            	sendsms phone-User4, message
            }            
        }
        
		if (myswitch-User5){
        	if (myswitch-User5.currentSwitch == "on"){
            	def message = "${name-user5.value} - Your wash is done!"
            	sendsms phone-User5, message
            }            
        } 
        
		if (myswitch-User6){
        	if (myswitch-User6.currentSwitch == "on"){
            	def message = "${name-user6.value} - Your wash is done!"
            	sendsms phone-User6, message
            }            
        }  
            
 sendPush "LAUNDRY TRIGGER!"           
            


//        if (phone) {
//            sendSms phone, message
//        } else {
//           sendPush message
//        }
        
        // JN - I dont know what this is
        //speechAlert(message)
    }
    else {
    	// Do Nothing, no change in either direction
    }
}

// JN - I dont know what this is
//private speechAlert(msg) {
//  speech.speak(msg)
//}

// JN - I dont know what this is
//private hideOptionsSection() {
//  (phone) ? false : true
//}