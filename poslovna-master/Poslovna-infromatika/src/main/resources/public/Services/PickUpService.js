(function(angular){

	var app = angular.module('app');

	app.service('PickUpService', function(){

		var vrednost;
		var column;
		var lookUpColumnName;
		var lookUpValue;
		var lookUpcolumn;

		var skracenica;
		var jedinicaMere;
		var idJedMere;

		var stopaPdvA;

		this.getStopaPdv = function() {
			return stopaPdvA;
		};
		this.setStopaPdv = function(value) {
			stopaPdvA = value;
		};

		this.getSkracenica = function(){
			return skracenica;
		};

		this.setSkracenica = function(value){
			skracenica = value;
		};

		this.getIdJedMere = function(){
			return idJedMere;
		};

		this.setIdJedMere = function(value){
			idJedMere = value;
		};

		this.setJedinicaMere = function(value) {
			jedinicaMere = value;
		};
		this.getJedinicaMere = function(){
			return jedinicaMere;
		};

		this.getVrednost = function(){
			return vrednost;
		};

		this.setVrednost = function(value){
			vrednost = value;
		};

		this.getColumn = function(){
			return column;
		};

		this.setColumn = function(col){
			column = col;
		};
		this.setLookUpValue = function(value) {
			lookUpValue = value;
		};
		this.getLookUpValue = function() {
			return lookUpValue;
		};
		this.setLookUpColumn = function(column) {
			lookUpcolumn =column;
		};
		this.getLookColumn = function(){
			return lookUpcolumn;
		};

	});

})
(angular);
