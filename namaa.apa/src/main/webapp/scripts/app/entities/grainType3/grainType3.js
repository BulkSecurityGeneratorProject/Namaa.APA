'use strict';

angular.module('namaaApaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('grainType3', {
                parent: 'entity',
                url: '/grainType3s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.grainType3.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grainType3/grainType3s.html',
                        controller: 'GrainType3Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grainType3');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('grainType3.detail', {
                parent: 'entity',
                url: '/grainType3/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.grainType3.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grainType3/grainType3-detail.html',
                        controller: 'GrainType3DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grainType3');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GrainType3', function($stateParams, GrainType3) {
                        return GrainType3.get({id : $stateParams.id});
                    }]
                }
            })
            .state('grainType3.new', {
                parent: 'grainType3',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grainType3/grainType3-dialog.html',
                        controller: 'GrainType3DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {chickpeas: null, beans: null, cowpea: null, lathyrus: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('grainType3', null, { reload: true });
                    }, function() {
                        $state.go('grainType3');
                    })
                }]
            })
            .state('grainType3.edit', {
                parent: 'grainType3',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grainType3/grainType3-dialog.html',
                        controller: 'GrainType3DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GrainType3', function(GrainType3) {
                                return GrainType3.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grainType3', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
