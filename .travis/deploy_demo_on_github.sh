#!/bin/bash
#
# Copyright (c) 2013, Anthony Schiochet and Eric Citaire
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
# * Redistributions of source code must retain the above copyright notice, this
#   list of conditions and the following disclaimer.
#
# * Redistributions in binary form must reproduce the above copyright notice,
#   this list of conditions and the following disclaimer in the documentation
#   and/or other materials provided with the distribution.
#
# * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
#   derived from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
# INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
# BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
# OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
# NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
# EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#


set -e

organization="gwtd3"
project_repo="gwt-d3"
site_repo="gwtd3.github.io"

if [ "${TRAVIS_PULL_REQUEST}" != "false" ] ; then
  echo "Building pull request #${TRAVIS_PULL_REQUEST}, skipping the deployment."
  exit 0
fi

if [ "${TRAVIS_REPO_SLUG}" != "${organization}/${project_repo}" ] ; then
  echo "Building fork '${TRAVIS_REPO_SLUG}', skipping the deployment."
  exit 0
fi

if [ "${TRAVIS_BRANCH}" != "master" ] ; then
  echo "Building branch '${TRAVIS_BRANCH}', skipping the deployment."
  exit 0
fi

demo_artifact="${PWD}/gwt-d3-demo/target/gwt-d3-demo.war"
private_key="${PWD}/id_rsa"

encrypted_key="${encrypted_25c5d1a53c1c_key}"
encrypted_iv="${encrypted_25c5d1a53c1c_iv}"

git config --global user.email "build@travis-ci.org"
git config --global user.name "Travis-CI"
git config --global push.default simple

openssl aes-256-cbc -K "${encrypted_key}" -iv "${encrypted_iv}" -in "${private_key}.enc" -out "${private_key}" -d
chmod 400 id_rsa

cat <<EOT > ~/.ssh/config
host github.com
 HostName github.com
 IdentityFile ${private_key}
 User git
EOT

git clone "git@github.com:${organization}/${site_repo}.git"

cd "${site_repo}"

if [ -d demo ] ; then
  git rm -r demo
fi
mkdir demo
cd demo

unzip "${demo_artifact}" -x "META-INF/*" "WEB-INF/*"

git add .
if git commit -m "Update demo" ; then
  git push
fi
cd ../..

rm -rf "${site_repo}"

rm -f "${private_key}"
