(ns leiningen.with-profile
  (:require [leiningen.core.main :as main]
            [leiningen.core.project :as project]))

(defn- with-profile*
  "Apply the given task with a comma-separeted profile list."
  [project profiles task-name & args]
  (let [profiles (map keyword (.split profiles ","))
        project (project/merge-profiles project profiles)]
    (main/apply-task task-name project args)))

(defn with-profile
  "Apply the given task with the profile(s) specified.

Comma-separated profiles may be given to merged profiles and perform the task.
Colon-separated profiles may be given for sequential profile application."
  [project profiles task-name & args]
  (let [profile-groups (seq (.split profiles ":"))]
    (doseq [profile-group profile-groups]
      (println (format "Performing task '%s' with profile(s): '%s'"
                       task-name profile-group))
      (apply with-profile* project profile-group task-name args))))