var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];
var bullets = [];

server.listen(8080,function(){
    console.log("Server is running...");
});

io.on('connection',function(socket){
	console.log("Player Connected");
	socket.emit('socketID',{id: socket.id});
	socket.emit('getPlayers', players);
	socket.emit('getBullets', bullets);
	socket.broadcast.emit('newPlayer',{id: socket.id});

	socket.on('playerMoved', function(data){
	    data.id = socket.id;
	    socket.broadcast.emit('playerMoved', data);
	    //console.log("PlayerMoved, ID: "+data.id+", X: "+data.x+", Y: "+data.y);
	    for(var i = 0; i < players.length; i++){
	        if(players[i].id == data.id){
                players[i].x = data.x;
                players[i].y = data.y;
                players[i].angle = data.angle;
	        }
	    }
	});

	socket.on('playerShoot', function(data){
	    data.id = socket.id;
	    socket.broadcast.emit('playerShoot', data);
	    //console.log("PlayerMoved, ID: "+data.id+", X: "+data.x+", Y: "+data.y);
	    for(var i = 0; i < bullets.length; i++){
	        if(bullets[i].id == data.id){
                bullets[i].x = data.x;
                bullets[i].y = data.y;
                bullets[i].angle = data.angle;
                bullets[i].color = data.color;
                bullets[i].newX = data.newX;
                bullets[i].newY = data.newY;
	        }
	    }
	});

	socket.on('disconnect',function(){
		console.log("Player Disconnected");
		socket.broadcast.emit('playerDisconnected', {id: socket.id});
		for(var x = 0; x < players.length; x++){
		    if(players[x].id == socket.id){
		        players.splice(x, 1);
		    }
		}
	});
	players.push(new player(socket.id, 0, 0, 0));
	bullets.push(new bullet(socket.id, 0, 0, 0, 0, 0, 0));
});

function player(id, x, y, angle){
    this.id = id;
    this.x = x;
    this.y = y;
    this.angle = angle;
}
function bullet(id, color, x, y, angle, dirX, dirY){
    this.id = id;
    this.x = x;
    this.y = y;
    this.angle = angle;
    this.color = color;
    this.dirX = dirX;
    this.dirY = dirY;
}