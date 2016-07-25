'use strict';

angular.module('namaaApaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('grainType2', {
                parent: 'entity',
                url: '/grainType2s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.grainType2.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grainType2/grainType2s.html',
                        controller: 'GrainType2Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grainType2');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('grainType2.detail', {
                parent: 'entity',
                url: '/grainType2/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.grainType2.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grainType2/grainType2-detail.html',
                        controller: 'GrainType2DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grainType2');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GrainType2', function($stateParams, GrainType2) {
                        return GrainType2.get({id : $stateParams.id});
                    }]
                }
            })
            .state('grainType2.new', {
                parent: 'grainType2',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grainType2/grainType2-dialog.html',
                        controller: 'GrainType2DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {wheat: null, barley: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('grainType2', null, { reload: true });
                    }, function() {
                        $state.go('grainType2');
                    })
                }]
            })
            .state('grainType2.edit', {
                parent: 'grainType2',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grainType2/grainType2-dialog.html',
                        controller: 'GrainType2DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GrainType2', function(GrainType2) {
                                return GrainType2.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grainType2', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
