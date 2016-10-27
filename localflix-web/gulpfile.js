var gulp = require('gulp');
var ts = require("gulp-typescript");
var sourcemaps = require('gulp-sourcemaps');
var babel = require("gulp-babel");
var uglify = require('gulp-uglify');

var tsProject = ts.createProject("tsconfig.json");


gulp.task('default', ['compile-typescript'], function() {
    if(process.env.NODE_ENV === 'production') {
        console.log('gulp runs as expected on production');
    } else {
        console.log('gulp runs as expected');
    }
});

gulp.task('compile-typescript', function() {
    return tsProject
        .src()
        .pipe(sourcemaps.init())
        .pipe(tsProject())
        .js
        .pipe(babel({
            presets: ['es2015']
        }))
        .pipe(uglify())
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest("src/main/webapp/js/"));
});

gulp.task('watch', function() {
    gulp.watch("src/main/webapp/js/**/*.ts", ['compile-typescript']);
});