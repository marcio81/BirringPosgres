'use strict';

describe('Controller Tests', function() {

    describe('Evaluar Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEvaluar, MockCervesa, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEvaluar = jasmine.createSpy('MockEvaluar');
            MockCervesa = jasmine.createSpy('MockCervesa');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Evaluar': MockEvaluar,
                'Cervesa': MockCervesa,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("EvaluarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:evaluarUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
